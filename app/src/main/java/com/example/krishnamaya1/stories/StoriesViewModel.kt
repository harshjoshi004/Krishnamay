package com.example.krishnamaya1.stories

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krishnamaya1.authentication.data.KrishnamayaUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class StoriesViewModel: ViewModel() {
    private val storiesRef = FirebaseDatabase.getInstance().getReference("stories")
    private val storageRef = FirebaseStorage.getInstance().getReference()
    private val usersRef = FirebaseDatabase.getInstance().getReference("users")

    private val _liveStories = MutableLiveData<List<Pair<Story, KrishnamayaUser>>>()
    val liveStories: MutableLiveData<List<Pair<Story, KrishnamayaUser>>> = _liveStories

    fun seeStory(storyId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        var seenList = mutableListOf<String>()
        FirebaseAuth.getInstance().currentUser?.uid?.let { userId->
            extractUserData(userId) {user->
                storiesRef.child(storyId).get()
                    .addOnSuccessListener { child->
                        child.getValue(Story::class.java)?.let { story->

                            story.seenBy.forEach { seenList.add(it) }
                            if (!seenList.contains(user.userName) && story.userId != userId) {
                                seenList.add(user.userName)
                            }

                            storiesRef.child(storyId).child("seenBy").setValue(seenList)
                                .addOnSuccessListener { onSuccess() }
                        }
                    }
                    .addOnFailureListener { e -> throw e }
            }
        }
    }

    fun getStoryById(storyId: String, onSuccess: (Story) -> Unit, onFailure: (String) -> Unit){
        storiesRef.child(storyId).get()
            .addOnSuccessListener { story->
                story.getValue(Story::class.java)?.let { onSuccess(it) }
            }
            .addOnFailureListener { e -> throw e }
    }

    fun extractUserData(userId:String, onSuccess:(KrishnamayaUser)->Unit) {
        usersRef.child(userId).get()
            .addOnSuccessListener { krishnamayaUser->
                val user = krishnamayaUser.getValue(KrishnamayaUser::class.java)
                user?.let { onSuccess(it) }
            }
            .addOnFailureListener { e -> throw e }
    }

    fun getStories(onSuccess: (List<Pair<Story, KrishnamayaUser>>) -> Unit, onFailure: (String) -> Unit) {
        val result = mutableListOf<Pair<Story, KrishnamayaUser>>()

        try {
            storiesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { child ->
                        child.getValue(Story::class.java)?.let { story ->
                            Log.e("HarshStory", "onDataChange: story = ${story.storyId}")
                            extractUserData(story.userId) { user ->
                                Log.e("HarshStory", "onDataChange: user = ${user.userId}")
                                result.add(story to user)

                                Log.e("HarshStory", "onDataChange: snapshotchildrensize =  ${snapshot.childrenCount.toInt()} & resultsize = ${result.size}")
                                if(snapshot.childrenCount.toInt() == result.size){
                                    Log.e("HarshStory", "onDataChange: result =  ${result}")
                                    onSuccess(result)
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                    throw error.toException()
                }

            })
        } catch (e: Exception) {
            onFailure(e.message?:"Something went wrong!")
        }
    }

    private fun uploadStoryImageToStorage(url: Uri, onSuccess: (String) -> Unit) {
        val imageLink = storageRef.child("stories/images/${UUID.randomUUID()}.jpg")

        imageLink.putFile(url).addOnSuccessListener{
            imageLink.downloadUrl.addOnSuccessListener{ uri->
                uri?.let { nonNullUri->

                    Log.d("uploadStoryImageToStorage: ", "Image Uploaded with Url: $nonNullUri")
                    onSuccess(nonNullUri.toString())
                }
            }.addOnFailureListener{throw it}
        }.addOnFailureListener{throw it}
    }

    private fun uploadStoryVideoToStorage(url: Uri, onSuccess: (String) -> Unit) {
        val videoLink = storageRef.child("stories/videos/${UUID.randomUUID()}.mp4")

        videoLink.putFile(url).addOnSuccessListener{
            videoLink.downloadUrl.addOnSuccessListener{ uri->
                uri?.let { nonNullUri->

                    Log.d("uploadStoryVideoToStorage: ", "Video Uploaded with Url: $nonNullUri")
                    onSuccess(nonNullUri.toString())
                }
            }.addOnFailureListener{e->throw e}
        }.addOnFailureListener{e->throw e}
    }

    private fun addStoryImage(url: Uri, onSuccess: () -> Unit) {
        uploadStoryImageToStorage(url) { link->
            FirebaseAuth.getInstance().currentUser?.uid.let { uid->
                val storyId = UUID.randomUUID().toString()
                val userId = uid.toString()

                val story = Story(
                    type = "image",
                    userId = userId,
                    storyId = storyId,
                    url = link,
                    seenBy = emptyList(),
                    seenByCount = 0
                )

                storiesRef.child(storyId).setValue(story).addOnSuccessListener{
                    onSuccess()
                }.addOnFailureListener{throw it}
            }
        }
    }

    private fun addStoryVideo(url: Uri, onSuccess: () -> Unit) {
        uploadStoryVideoToStorage(url) { link->
            FirebaseAuth.getInstance().currentUser?.uid.let { uid->
                val storyId = UUID.randomUUID().toString()
                val userId = uid.toString()

                val story = Story(
                    type = "video",
                    userId = userId,
                    storyId = storyId,
                    url = link,
                    seenBy = emptyList(),
                    seenByCount = 0
                )

                storiesRef.child(storyId).setValue(story).addOnSuccessListener{
                    onSuccess()
                }.addOnFailureListener{throw it}
            }
        }
    }

    fun addStory(url: Uri, type:String, onSuccess: () -> Unit, onFailure: (String)->Unit) {
        try {
            if(type == "image")
                addStoryImage(url, onSuccess)
            else
                addStoryVideo(url, onSuccess)
        } catch (e: Exception) {
            Log.e("StoriesViewModel", "addStory: ${e.message ?: "Something went wrong"}")
            onFailure(e.message ?: "Something went wrong")
        }
    }
}
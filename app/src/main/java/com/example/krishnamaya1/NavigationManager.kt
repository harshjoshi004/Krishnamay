package com.example.krishnamaya1

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.krishnamaya1.aboutPage.AboutUs
import com.example.krishnamaya1.addPost.presentation.AddPostUI
import com.example.krishnamaya1.contactAndSupport.ChantFlow
import com.example.krishnamaya1.contactAndSupport.ContactAndSupportUI
import com.example.krishnamaya1.contactAndSupport.WallpaperPage
import com.example.krishnamaya1.discussions.presentation.DiscussionFeed
import com.example.krishnamaya1.homeFeed.presentation.HomeScreenUI
import com.example.krishnamaya1.krishnaStore.KrishnaStoreUi
import com.example.krishnamaya1.mantras.MantraList
import com.example.krishnamaya1.mantras.mantras
import com.example.krishnamaya1.profile.presentation.EditProfileScreen
import com.example.krishnamaya1.profile.presentation.ProfileScreenUI
import com.example.krishnamaya1.searchUser.presentation.SearchDetailScreen
import com.example.krishnamaya1.searchUser.presentation.SearchUsersScreen
import com.example.krishnamaya1.stories.AddImageStoryScreen
import com.example.krishnamaya1.stories.AddVideoStoryScreen
import com.example.krishnamaya1.stories.StoryPresenter
import com.example.krishnamaya1.stories.StoryPresenter2
import com.example.krishnamaya1.vedabase.VedabaseUi

@Composable
fun NavigationManager(
    navController: NavController,
    viewModel: MainViewModel,
    padval: PaddingValues,
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.BottomBarScreens.HomeScreen.bRoute,
        modifier = Modifier.padding(padval)
    ){
        // Bottom Bar Screens
        composable(Screens.BottomBarScreens.HomeScreen.bRoute){
            viewModel.setCurrentScreen(Screens.BottomBarScreens.HomeScreen)
            HomeScreenUI(navController, viewModel)
        }
        composable(Screens.BottomBarScreens.Profile.bRoute){
            viewModel.setCurrentScreen(Screens.BottomBarScreens.Profile)
            ProfileScreenUI(navController,viewModel)
        }
        composable(Screens.BottomBarScreens.Vedabase.bRoute){
            viewModel.setCurrentScreen(Screens.BottomBarScreens.Vedabase)
            VedabaseUi()
        }
        composable(Screens.BottomBarScreens.SearchUsers.bRoute){
            viewModel.setCurrentScreen(Screens.BottomBarScreens.SearchUsers)
            SearchUsersScreen(navController)
        }
        composable(Screens.BottomBarScreens.Discussion.bRoute){
            viewModel.setCurrentScreen(Screens.BottomBarScreens.Discussion)
            DiscussionFeed()
        }

        // Nav Drawer Screens
        composable(Screens.DrawerScreens.SpecialMantras.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.SpecialMantras)
            MantraList(mantras)
        }
        composable(Screens.DrawerScreens.ChantFlow.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.ChantFlow)
            ChantFlow()
        }
        composable(Screens.DrawerScreens.BhagwadGeetaNotes.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.BhagwadGeetaNotes)
            BhagwadGeetaNotesScreenUI(viewModel)
        }
        composable(Screens.DrawerScreens.MediaLibrary.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.MediaLibrary)
            KrishnaStoreUi()
        }
        composable(Screens.DrawerScreens.VaishnavCalendar.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.VaishnavCalendar)
            VaishnavCalendarScreenUI(viewModel)
        }
        composable(Screens.DrawerScreens.Wallpapers.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.Wallpapers)
            WallpaperPage()
        }
        composable(Screens.DrawerScreens.ContactUs.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.ContactUs)
            ContactAndSupportUI()
        }
        composable(Screens.DrawerScreens.Donate.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.Donate)
            DonateScreenUI(viewModel)
        }
        composable(Screens.DrawerScreens.Share.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.Share)
            ShareScreenUI(viewModel)
        }
        composable(Screens.DrawerScreens.AboutUs.dRoute){
            viewModel.setCurrentScreen(Screens.DrawerScreens.AboutUs)
            AboutUs()
        }

        // secondary screens
        composable("add-post"){
            AddPostUI(navController = navController)
        }

        composable("search-detail/{uid}"){ backStack->
            val uid = backStack.arguments?.getString("uid")
            SearchDetailScreen(uid = uid) {
                navController.popBackStack()
            }
        }

        composable("addvidstory"){ backStack->
            AddVideoStoryScreen(navController)
        }

        composable("addimagestory"){ backStack->
            AddImageStoryScreen(navController)
        }

        composable("search-detail/{uid}"){ backStack->
            val uid = backStack.arguments?.getString("uid")
            SearchDetailScreen(uid = uid) {
                navController.popBackStack()
            }
        }

        composable("story-presenter/{storyId}"){ backStack->
            val id = backStack.arguments?.getString("storyId")
            StoryPresenter(storyId = id, navController = navController)
        }

        composable("story-presenter2/{storyId}"){ backStack->
            val id = backStack.arguments?.getString("storyId")
            StoryPresenter2(storyId = id, navController = navController)
        }

        composable("edit-user/{uid}"){ backStack->
            val uid = backStack.arguments?.getString("uid")
            EditProfileScreen(curUserId = uid) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun BhagwadGeetaNotesScreenUI(viewModel: MainViewModel) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                setLayerType(WebView.LAYER_TYPE_HARDWARE, null) // Enable hardware acceleration

                webViewClient = WebViewClient()
                loadUrl("https://www.holy-bhagavad-gita.org/index")
            }
        }
    )
}

@Composable
fun MediaLibraryScreenUI(viewModel: MainViewModel) {
    Text(text = viewModel.currentScreen.value.title)
}

@Composable
fun VaishnavCalendarScreenUI(viewModel: MainViewModel) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                setLayerType(WebView.LAYER_TYPE_HARDWARE, null) // Enable hardware acceleration

                webViewClient = WebViewClient()
                loadUrl("https://www.purebhakti.com/resources/vaisnava-calendar")
            }
        }
    )
}


@Composable
fun DonateScreenUI(viewModel: MainViewModel) {
    val donatepage = "https://vedabase.io/en/donate/"
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                setLayerType(WebView.LAYER_TYPE_HARDWARE, null) // Enable hardware acceleration

                webViewClient = WebViewClient()
                loadUrl(donatepage)
            }
        }
    )
}

@Composable
fun ShareScreenUI(viewModel: MainViewModel) {
    Text(text = viewModel.currentScreen.value.title)
}

@Composable
fun AboutUsScreenUI(viewModel: MainViewModel) {
    Text(text = viewModel.currentScreen.value.title)
}
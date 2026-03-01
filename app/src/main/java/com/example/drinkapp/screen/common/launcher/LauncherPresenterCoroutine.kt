package com.example.drinkapp.screen.common.launcher

import android.util.Log
import com.example.drinkapp.data.repository.UserRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Coroutine-based LauncherPresenter with Hilt DI
 */
class LauncherPresenterCoroutine @Inject constructor(
    private val repository: UserRepository
) : LauncherContract.Presenter, CoroutineScope {

    private var view: LauncherContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun attachView(view: LauncherContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun checkRoleClient(id: Long) {
        launch {
            try {
                // Add 10 second timeout for API call
                val result = withTimeout(10_000L) {
                    repository.getUserById(id)
                }
                
                when (result) {
                    is Result.Success -> {
                        if (result.data.role == 1L) {
                            view?.activeAccount()
                        } else {
                            view?.accountIsBlocked()
                        }
                    }
                    is Result.Error -> {
                        // Log error details for debugging
                        Log.e(
                            "LauncherPresenter",
                            "API Error checking user role - UserId: $id, " +
                            "Error: ${result.exception.message}, " +
                            "Timestamp: ${System.currentTimeMillis()}",
                            result.exception
                        )
                        // Navigate to MainActivity as graceful fallback (allows offline/cached functionality)
                        view?.activeAccount()
                    }
                    is Result.HttpError -> {
                        // Log HTTP error details for debugging
                        Log.e(
                            "LauncherPresenter",
                            "HTTP Error checking user role - UserId: $id, " +
                            "Status: ${result.code}, " +
                            "Message: ${result.message}, " +
                            "Timestamp: ${System.currentTimeMillis()}"
                        )
                        // Navigate to MainActivity as graceful fallback
                        view?.activeAccount()
                    }
                }
            } catch (e: TimeoutCancellationException) {
                // Log timeout event
                Log.e(
                    "LauncherPresenter",
                    "Timeout checking user role - UserId: $id, " +
                    "Duration: 10 seconds, " +
                    "Timestamp: ${System.currentTimeMillis()}",
                    e
                )
                // Navigate to MainActivity on timeout
                view?.activeAccount()
            } catch (e: Exception) {
                // Catch any other unexpected exceptions
                Log.e(
                    "LauncherPresenter",
                    "Unexpected error checking user role - UserId: $id, " +
                    "Error: ${e.message}, " +
                    "Timestamp: ${System.currentTimeMillis()}",
                    e
                )
                // Navigate to MainActivity as graceful fallback
                view?.activeAccount()
            }
        }
    }

    override fun onStart() {
        // Lifecycle method - can be used for subscriptions
    }

    override fun onStop() {
        job.cancel() // Cancel all coroutines
        detachView()
    }
}

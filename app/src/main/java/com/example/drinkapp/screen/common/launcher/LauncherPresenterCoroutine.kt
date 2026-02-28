package com.example.drinkapp.screen.common.launcher

import com.example.drinkapp.data.repository.UserRepository
import com.example.drinkapp.data.resource.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
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
            when (val result = repository.getUserById(id)) {
                is Result.Success -> {
                    if (result.data.role == 1L) {
                        view?.activeAccount()
                    } else {
                        view?.accountIsBlocked()
                    }
                }
                is Result.Error -> {
                    // Silent fail as per original implementation
                }
                is Result.HttpError -> {
                    // Silent fail as per original implementation
                }
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

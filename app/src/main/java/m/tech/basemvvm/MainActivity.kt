package m.tech.basemvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import m.tech.basemvvm.viewmodels.MainViewModel
import m.tech.basemvvm.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    val TAG = "AppDebug"

    @Inject
    lateinit var provider: ViewModelProviderFactory

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, provider).get(MainViewModel::class.java)

        subscribeObserver()

        start.setOnClickListener {
//            viewModel.getPosts()
            viewModel.getPostsNoSaveDb()
        }

        stop.setOnClickListener {
            viewModel.cancelActiveJobs()
        }
    }

    private fun subscribeObserver() {
        viewModel.listPosts.observe(this, Observer { dataState ->
            Log.d(TAG, "Loading...")

            dataState.error?.let { event ->
                Log.d(TAG, "Error handling... Cancel loading here")
                event.getContentIfNotHandled()?.let {
                    Log.d(TAG, "Error not handled $it")
                }
            }

            dataState.data?.let {
                Log.d(TAG, "Data handling... Cancel loading here")
                Log.d(TAG, "Got posts returned from API, setting to ViewState... ${it.size}")
            }
        })

        viewModel.postResponse.observe(this, Observer { dataState ->
            Log.d(TAG, "Loading...")

            dataState.error?.let { event ->
                Log.d(TAG, "Error handling... Cancel loading here")
                event.getContentIfNotHandled()?.let {
                    Log.d(TAG, "Error not handled $it")
                }
            }

            dataState.data?.let {
                Log.d(TAG, "Data handling... Cancel loading here")
                Log.d(TAG, "Got posts returned from a POST API, setting to ViewState... $it")
            }
        })
    }
}

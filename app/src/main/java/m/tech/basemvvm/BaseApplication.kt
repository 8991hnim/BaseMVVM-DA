package m.tech.basemvvm

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import m.tech.basemvvm.di.DaggerAppComponent


class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}
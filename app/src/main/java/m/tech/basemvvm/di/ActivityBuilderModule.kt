package m.tech.basemvvm.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import m.tech.basemvvm.MainActivity
import m.tech.basemvvm.di.main.MainFragmentBuildersModule
import m.tech.basemvvm.di.main.MainModule
import m.tech.basemvvm.di.main.MainScope
import m.tech.basemvvm.di.main.MainViewModelModule

@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [MainFragmentBuildersModule::class, MainModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}
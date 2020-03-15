package m.tech.basemvvm.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import m.tech.basemvvm.viewmodels.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}
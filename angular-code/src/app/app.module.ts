import { LoaderInterceptorService } from './shared/services/loader-interceptor.service';
import { LoaderComponent } from './layouts/loader/loader.component';
import * as $ from 'jquery';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LocationStrategy, PathLocationStrategy } from '@angular/common';
import { AppComponent } from './app.component';

import { FlexLayoutModule } from '@angular/flex-layout';
import { FullComponent } from './layouts/full/full.component';
import { AppHeaderComponent } from './layouts/full/header/header.component';
import { AppSidebarComponent } from './layouts/full/sidebar/sidebar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material-module';

import { SpinnerComponent } from './layouts/spinner.component';
import { AppRoutingModule } from './app.routing.module';
import { PaginaNaoEncontradaComponent } from './shared/pagina-nao-encontrada/pagina-nao-encontrada.component';
import { LayoutsModule } from './layouts/layouts.module';
import { AuthGuard } from './shared/auth.guard';
import { SharedService } from './shared/shared.service';
import { LoggedUserService } from './shared/services/logged-user.service';
import { FileService } from './shared/services/file.service';
import { LogoffService } from './shared/services/logoff.service';

@NgModule({
  declarations: [
    AppComponent,
    FullComponent,
    AppHeaderComponent,
    SpinnerComponent,
    AppSidebarComponent,
    LoaderComponent,
    PaginaNaoEncontradaComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    FlexLayoutModule,
    HttpClientModule,
    LayoutsModule,
    AppRoutingModule
  ],
  providers: [
    LoggedUserService,
    FileService,
    LogoffService,
    AuthGuard,
    SharedService,
    {
      provide: LocationStrategy,
      useClass: PathLocationStrategy
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoaderInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

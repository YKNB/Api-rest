import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { MainIdeaComponent } from './components/main-idea/main-idea.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {HttpClientService} from './shared//services/http-client.service';
import { AddressComponent } from './components/address/address.component';
import { UserComponent } from './components/user/user.component';
import { RegisterComponent } from './components/register/register.component';
import { MessageModalComponent } from './components/message-modal/message-modal.component';
import {FormsModule} from '@angular/forms';
import { GestionnaireUserComponent } from './components/gestionnaire-user/gestionnaire-user.component';
import { GestionnaireAddressComponent } from './components/gestionnaire-address/gestionnaire-address.component';
import {UserService} from './shared/services/user.service';
import {AddressService} from './shared//services/address.service';
import {AuthenticationService} from './shared//services/authentication.service';
import {MatSelectModule} from '@angular/material/select';
import { UserDetailsModalComponent } from './components/user-details-modal/user-details-modal.component';
import { MeComponent } from './components/me/me.component';
import { AddressDetailsModalComponent } from './components/address-details-modal/address-details-modal.component';
import { GestionnaireBookComponent } from './components/gestionnaire-book/gestionnaire-book.component';
import { ElementComponent } from './components/element/element.component';
import { AddelementComponent } from './components/addelement/addelement.component';
import { ProductsComponent } from './components/products/products.component';
import { Page404Component } from './components/page404/page404.component';
import { SpinnerComponent } from './components/spinner/spinner.component';
import { HomeComponent } from './components/home/home.component';
import { LoadingInterceptor } from './shared/loading.interceptor';
import { MatDialogModule } from '@angular/material/dialog';
import { SuccessDialogComponent } from './components/success-dialog/success-dialog.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MainIdeaComponent,
    AddressComponent,
    UserComponent,
    RegisterComponent,
    MessageModalComponent,
    GestionnaireUserComponent,
    GestionnaireAddressComponent,
    UserDetailsModalComponent,
    MeComponent,
    AddressDetailsModalComponent,
    GestionnaireBookComponent,
    AddelementComponent,
    ElementComponent,
    ProductsComponent,
    Page404Component,
    SpinnerComponent,
    HomeComponent,
    SuccessDialogComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule,
    MatSelectModule,
    MatDialogModule,
    BrowserAnimationsModule,
  ],
  providers: [HttpClientService, HttpClient,AuthenticationService, UserService,AddressService,    {
    provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }

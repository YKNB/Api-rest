import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { BodyComponent } from './body/body.component';
import { MainIdeaComponent } from './body/main-idea/main-idea.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {HttpClientService} from './services/http-client.service';
import { AddressComponent } from './body/address/address.component';
import { UserComponent } from './body/user/user.component';
import { RegisterComponent } from './body/register/register.component';
import { MessageModalComponent } from './modal/message-modal/message-modal.component';
import {FormsModule} from '@angular/forms';
import { GestionnaireUserComponent } from './body/gestionnaire-user/gestionnaire-user.component';
import { GestionnaireAddressComponent } from './body/gestionnaire-address/gestionnaire-address.component';
import {UserService} from './services/user.service';
import {AddressService} from './services/address.service';
import {AuthenticationService} from './services/authentication.service';
import {MatSelectModule} from '@angular/material/select';
import { UserDetailsModalComponent } from './body/user-details-modal/user-details-modal.component';
import { MeComponent } from './body/me/me.component';
import { AddressDetailsModalComponent } from './body/address-details-modal/address-details-modal.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    BodyComponent,
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

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule,
    MatSelectModule
  ],
  providers: [HttpClientService, HttpClient,AuthenticationService, UserService,AddressService],
  bootstrap: [AppComponent]
})
export class AppModule { }

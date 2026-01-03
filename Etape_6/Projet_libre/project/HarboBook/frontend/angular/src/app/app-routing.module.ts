import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainIdeaComponent} from './components/main-idea/main-idea.component';
import {AddressComponent} from './components/address/address.component';
import {UserComponent} from './components/user/user.component';
import {GestionnaireUserComponent} from './components/gestionnaire-user/gestionnaire-user.component';
import {GestionnaireAddressComponent} from './components/gestionnaire-address/gestionnaire-address.component';
import { MeComponent } from './components/me/me.component';
import { ProductsComponent } from './components/products/products.component';
import { ElementComponent } from './components/element/element.component';
import { AddelementComponent } from './components/addelement/addelement.component';
import { GestionnaireBookComponent } from './components/gestionnaire-book/gestionnaire-book.component';
import { AuthGuardService } from './shared/services/auth-guard.service';
import { Page404Component } from './components/page404/page404.component';
import { HomeComponent } from './components/home/home.component';

const appRoutes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'register', component: UserComponent},
  {path: 'gestionnaireUser', component: GestionnaireUserComponent, canActivate: [AuthGuardService]},
  {path: 'gestionnaireAddress', component: GestionnaireAddressComponent, canActivate: [AuthGuardService]},
  {path: 'address', component: AddressComponent, canActivate: [AuthGuardService]},
  {path: 'profile', component: MeComponent, canActivate: [AuthGuardService]},
  {path: 'products', component: ProductsComponent, canActivate: [AuthGuardService]},
  {path: 'gestionnaireBook', component: GestionnaireBookComponent, canActivate: [AuthGuardService]},
  {path: 'element', component: ElementComponent, canActivate: [AuthGuardService]},
  {path: 'addelement', component: AddelementComponent, canActivate: [AuthGuardService]},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: '**', pathMatch: 'full', component: Page404Component  }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}

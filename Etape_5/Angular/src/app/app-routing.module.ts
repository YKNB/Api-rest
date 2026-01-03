import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainIdeaComponent} from './body/main-idea/main-idea.component';
import {AddressComponent} from './body/address/address.component';
import {UserComponent} from './body/user/user.component';
import {GestionnaireUserComponent} from './body/gestionnaire-user/gestionnaire-user.component';
import {GestionnaireAddressComponent} from './body/gestionnaire-address/gestionnaire-address.component';
import { MeComponent } from './body/me/me.component';

const appRoutes: Routes = [
  {path: 'home', component: MainIdeaComponent},
  {path: 'user', component: UserComponent},
  {path: 'gestionnaireUser', component: GestionnaireUserComponent},
  {path: 'gestionnaireAddress', component: GestionnaireAddressComponent},
  {path: 'address', component: AddressComponent},
  {path: 'profile', component: MeComponent},
  {path: '**', redirectTo: '/home', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}

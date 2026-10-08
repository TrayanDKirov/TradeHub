import { Routes } from '@angular/router';
import { BuyerRegisterComponent } from './components/user/register/buyer-register/buyer-register.component';
import { SellerRegisterComponent } from './components/user/register/seller-register/seller-register.component';
import { LoginComponent } from './components/user/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import {HomeComponent} from './components/home/home.component';
import {BuyerComponent} from './components/user/profile/buyer/buyer.component';
import {SellerComponent} from './components/user/profile/seller/seller.component';
import {ShopListComponent} from './components/shop/shop-list/shop-list.component';
import {ShopFormComponent} from './components/shop/shop-form/shop-form.component';
import {ShopViewComponent} from './components/shop/shop-view/shop-view.component';
import {ShopItemFormComponent} from './components/shop-item/shop-item-form/shop-item-form.component';
import {ShopItemViewComponent} from './components/shop-item/shop-item-view/shop-item-view.component';
import {CartComponent} from './components/cart/cart.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },

  { path: 'register', redirectTo: 'register/buyer', pathMatch: 'full' },
  { path: 'register/buyer', component: BuyerRegisterComponent },
  { path: 'register/seller', component: SellerRegisterComponent },
  { path: 'login', component: LoginComponent},

  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'profile/buyer', component: BuyerComponent, canActivate: [AuthGuard]},
  { path: 'profile/seller', component: SellerComponent, canActivate: [AuthGuard]},

  { path: 'shops', component: ShopListComponent, canActivate: [AuthGuard]},
  { path: 'shops/my', component: ShopListComponent, canActivate: [AuthGuard], data: {mine: true}},
  { path: 'shops/create', component: ShopFormComponent, canActivate: [AuthGuard]},
  { path: 'shop/:id', component: ShopViewComponent, canActivate: [AuthGuard]},
  { path: 'shop/:shopId/items/create', component: ShopItemFormComponent, canActivate: [AuthGuard]},
  { path: 'item/:id', component: ShopItemViewComponent, canActivate: [AuthGuard]},
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard]},
  { path: '**', redirectTo: 'home', pathMatch: 'full'}
];

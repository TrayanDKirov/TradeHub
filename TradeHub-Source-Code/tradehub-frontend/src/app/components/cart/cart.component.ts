import {ChangeDetectorRef, Component} from '@angular/core';
import {CurrencyPipe} from '@angular/common';
import {RouterLink} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
import {CartService} from '../../services/cart/cart.service';
import {CartResponseDto} from '../../dtos/cart/cart-response.dto';
import {CartItemResponseDto} from '../../dtos/cart/cart-item-response.dto';
import {environment} from '../../../environments/environment';

interface CartShopGroup {
  shopId: number;
  shopName: string;
  items: CartItemResponseDto[];
  subtotal: number;
}

@Component({
  imports: [MatButtonModule, MatIconModule, MatCardModule, MatDividerModule, RouterLink, CurrencyPipe],
  selector: 'app-cart',
  styleUrl: './cart.component.css',
  templateUrl: './cart.component.html',
})
export class CartComponent {
  cart: CartResponseDto | undefined;
  groups: CartShopGroup[] = [];

  constructor(private service: CartService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.service.getCart().subscribe(cart => this.setCart(cart));
  }

  pictureSrc(item: CartItemResponseDto): string {
    return `${environment.host}${item.pictureUrl}`;
  }

  changeQuantity(item: CartItemResponseDto, delta: number) {
    const quantity = item.quantity + delta;
    if (quantity < 1 || quantity > item.inStock) {
      return;
    }

    this.service.updateItem(item.id, {quantity}).subscribe(cart => this.setCart(cart));
  }

  removeItem(item: CartItemResponseDto) {
    this.service.removeItem(item.id).subscribe(cart => this.setCart(cart));
  }

  clearCart() {
    this.service.clearCart().subscribe(cart => this.setCart(cart));
  }

  private setCart(cart: CartResponseDto) {
    this.cart = cart;
    this.groups = this.groupByShop(cart.items);
    this.cdr.markForCheck();
  }

  private groupByShop(items: CartItemResponseDto[]): CartShopGroup[] {
    const groups = new Map<number, CartShopGroup>();

    for (const item of items) {
      let group = groups.get(item.shopId);
      if (!group) {
        group = {shopId: item.shopId, shopName: item.shopName, items: [], subtotal: 0};
        groups.set(item.shopId, group);
      }

      group.items.push(item);
      group.subtotal += item.totalPrice;
    }

    return [...groups.values()];
  }
}
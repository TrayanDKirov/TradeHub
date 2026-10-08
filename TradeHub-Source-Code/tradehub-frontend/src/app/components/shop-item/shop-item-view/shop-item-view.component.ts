import {ChangeDetectorRef, Component} from '@angular/core';
import {CurrencyPipe} from '@angular/common';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {ShopItemResponseDto} from '../../../dtos/shop-item/shop-item-response.dto';
import {ShopItemService} from '../../../services/shop-item/shop-item.service';
import {environment} from '../../../../environments/environment';
import {CartService} from '../../../services/cart/cart.service';
import {UserService} from '../../../services/user/user.service';

@Component({
  imports: [MatButtonModule, MatIconModule, MatListModule, RouterLink, CurrencyPipe],
  selector: 'app-shop-item-view',
  styleUrl: './shop-item-view.component.css',
  templateUrl: './shop-item-view.component.html',
})
export class ShopItemViewComponent {
  item: ShopItemResponseDto | undefined;
  isBuyer = false;
  quantity = 1;

  constructor(private service: ShopItemService,
    private cartService: CartService,
    private userService: UserService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.service.getShopItem(id)
      .subscribe(item => {
        this.item = item;
        this.cdr.markForCheck();
      });

    this.userService.getUserPreview()
      .subscribe(userPreview => {
        this.isBuyer = userPreview.role.toLowerCase() === 'buyer';
        this.cdr.markForCheck();
      });
  }

  changeQuantity(delta: number) {
    if (!this.item) {
      return;
    }

    this.quantity = Math.min(Math.max(this.quantity + delta, 1), this.item.quantity);
  }

  addToCart() {
    if (!this.item) {
      return;
    }

    this.cartService.addItem({itemId: this.item.id, quantity: this.quantity})
      .subscribe(() => {
        this.quantity = 1;
        this.cdr.markForCheck();
      });
  }

  get pictureSrc(): string {
    return `${environment.host}${this.item?.pictureUrl ?? ''}`;
  }
}
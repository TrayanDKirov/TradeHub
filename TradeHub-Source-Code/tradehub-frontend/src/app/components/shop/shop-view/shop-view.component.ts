import {ChangeDetectorRef, Component} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {ShopResponseDto} from '../../../dtos/shop/shop-response.dto';
import {ShopService} from '../../../services/shop/shop.service';
import {ShopItemService} from '../../../services/shop-item/shop-item.service';
import {environment} from '../../../../environments/environment';
import {ShopItemCardComponent} from '../../shop-item/shop-item-card/shop-item-card.component';
import {UserService} from '../../../services/user/user.service';
import {AuthenticationService} from '../../../services/user/authentication.service';

@Component({
  imports: [MatButtonModule, MatIconModule, MatListModule, RouterLink, ShopItemCardComponent],
  selector: 'app-shop-view',
  styleUrl: './shop-view.component.css',
  templateUrl: './shop-view.component.html',
})
export class ShopViewComponent {
  shop: ShopResponseDto | undefined;
  currentUserId: number | undefined;
  isAdmin = false;
  isSeller = false;

  constructor(private service: ShopService,
    private shopItemService: ShopItemService,
    private userService: UserService,
    private authService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.isAdmin = this.authService.isAdmin();
    this.isSeller = this.authService.getRole()?.toUpperCase() === 'SELLER';

    this.service.getShop(id)
      .subscribe(shop => {
        this.shop = shop;
        this.cdr.markForCheck();
      });

    this.userService.getUserPreview()
      .subscribe(userPreview => {
        this.currentUserId = userPreview.id;
        this.cdr.markForCheck();
      });
  }

  deleteShop() {
    if (!this.shop || !confirm(`Delete "${this.shop.name}" and all of its items?`)) {
      return;
    }

    this.service.deleteShop(this.shop.id)
      .subscribe(() => this.router.navigate([this.shopsRoute]));
  }

  deleteItem(itemId: number) {
    if (!confirm('Delete this item?')) return;

    this.shopItemService.deleteShopItem(itemId).subscribe(() => {
      if (this.shop) {
        this.shop = { ...this.shop, items: this.shop.items.filter(i => i.id !== itemId) };
        this.cdr.markForCheck();
      }
    });
  }

  get shopsRoute(): string {
    return this.isSeller ? '/shops/my' : '/shops';
  }

  get isOwner(): boolean {
    return this.shop !== undefined && this.shop.seller.id === this.currentUserId;
  }

  get canManage(): boolean {
    return this.isOwner || this.isAdmin;
  }

  get pictureSrc(): string {
    return `${environment.host}${this.shop?.pictureUrl ?? ''}`;
  }
}

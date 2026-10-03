import {ChangeDetectorRef, Component} from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {ShopPreviewDto} from '../../../dtos/shop/shop-preview.dto';
import {ShopService} from '../../../services/shop/shop.service';
import {environment} from '../../../../environments/environment';

@Component({
  imports: [MatCardModule, MatButtonModule, RouterLink],
  selector: 'app-shop-list',
  styleUrl: './shop-list.component.css',
  templateUrl: './shop-list.component.html',
})
export class ShopListComponent {
  shops: ShopPreviewDto[] = [];

  constructor(private shopService: ShopService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.getShops();
  }

  // The backend runs on another origin, so a relative picture URL must be prefixed with its host
  pictureSrc(shop: ShopPreviewDto): string {
    return `${environment.host}${shop.pictureUrl}`;
  }

  getShops() {
    this.shopService.getShops().subscribe(
      shops => {
        this.shops = shops;
        this.cdr.markForCheck();
      }
    )
  }

  viewShop(id: number) {
    this.router.navigate([`/shop/${id}`], { relativeTo: this.route });
  }

  deleteShop(id: number) {

  }
}

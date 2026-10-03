import {ChangeDetectorRef, Component} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {ShopResponseDto} from '../../../dtos/shop/shop-response.dto';
import {ShopService} from '../../../services/shop/shop.service';
import {environment} from '../../../../environments/environment';

@Component({
  imports: [MatButtonModule, MatIconModule, MatListModule, RouterLink],
  selector: 'app-shop-view',
  styleUrl: './shop-view.component.css',
  templateUrl: './shop-view.component.html',
})
export class ShopViewComponent {
  shop: ShopResponseDto | undefined;

  constructor(private service: ShopService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.service.getShop(id)
      .subscribe(shop => {
        this.shop = shop;
        this.cdr.markForCheck();
      });
  }

  // The backend runs on another origin, so a relative picture URL must be prefixed with its host
  get pictureSrc(): string {
    return `${environment.host}${this.shop?.pictureUrl ?? ''}`;
  }
}
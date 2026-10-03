import {ChangeDetectorRef, Component, DestroyRef} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {Observable, switchMap} from 'rxjs';
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
  mine = false;

  constructor(private shopService: ShopService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef,
    private destroyRef: DestroyRef) {
  }

  ngOnInit() {
    this.mine = this.route.snapshot.data['mine'] === true;

    this.route.queryParamMap
      .pipe(
        switchMap(params => this.loadShops(params.get('userId'))),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe(shops => {
        this.shops = shops;
        this.cdr.markForCheck();
      });
  }

  private loadShops(userId: string | null): Observable<ShopPreviewDto[]> {
    if (this.mine) {
      return this.shopService.getMyShops();
    }

    return userId === null
      ? this.shopService.getShops()
      : this.shopService.getShops(Number(userId));
  }

  pictureSrc(shop: ShopPreviewDto): string {
    return `${environment.host}${shop.pictureUrl}`;
  }

  viewShop(id: number) {
    this.router.navigate([`/shop/${id}`], { relativeTo: this.route });
  }

  deleteShop(id: number) {

  }
}

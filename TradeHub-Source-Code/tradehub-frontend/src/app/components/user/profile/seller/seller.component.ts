import {ChangeDetectorRef, Component} from '@angular/core';
import {UserService} from '../../../../services/user/user.service';
import {SellerResponseDto} from '../../../../dtos/user/seller/seller-response.dto';
import {MatList, MatListItem} from '@angular/material/list';
import {MatFabButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {RouterLink} from '@angular/router';

@Component({
  imports: [
    MatList,
    MatListItem,
    MatFabButton,
    MatIcon,
    RouterLink
  ],
  selector: 'app-seller',
  styleUrl: './seller.component.css',
  templateUrl: './seller.component.html',
})
export class SellerComponent {
  seller: SellerResponseDto | undefined;

  constructor(private service: UserService,
              private cdr: ChangeDetectorRef
  ) {  }

  ngOnInit() {
    this.service.getSeller()
      .subscribe(seller => {
        this.seller = seller;
        this.cdr.markForCheck();
      })
  }
}

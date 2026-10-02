import {ChangeDetectorRef, Component} from '@angular/core';
import {MatListModule} from '@angular/material/list';
import {BuyerResponseDto} from '../../../../dtos/user/buyer/buyer-response.dto';
import {UserService} from '../../../../services/user/user.service';
import {RouterLink} from '@angular/router';
import {MatFabButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';

@Component({
  imports: [MatListModule, MatFabButton, MatIcon, RouterLink],
  selector: 'app-buyer',
  styleUrl: './buyer.component.css',
  templateUrl: './buyer.component.html',
})
export class BuyerComponent {
  buyer: BuyerResponseDto | undefined;

  constructor(private service: UserService,
              private cdr: ChangeDetectorRef
  ) {  }

  ngOnInit() {
    this.service.getBuyer()
      .subscribe(buyer => {
        this.buyer = buyer;
        this.cdr.markForCheck();
      })
  }
}

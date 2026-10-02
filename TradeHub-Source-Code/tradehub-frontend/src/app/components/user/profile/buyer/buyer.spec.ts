import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BuyerComponent } from './buyer.component';

describe('Buyer', () => {
  let component: BuyerComponent;
  let fixture: ComponentFixture<BuyerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BuyerComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

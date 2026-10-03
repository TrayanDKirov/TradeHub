import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ShopFormComponent } from './shop-form.component';

describe('ShopCreate', () => {
  let component: ShopFormComponent;
  let fixture: ComponentFixture<ShopFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShopFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ShopFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

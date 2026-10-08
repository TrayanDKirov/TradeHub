import { SellerInfoDto } from "../user/seller/seller-info.dto";
import { ShopItemPreviewDto } from "../shop-item/shop-item-preview.dto";

export interface ShopResponseDto {
  id: number;
  name: string;
  pictureUrl: string;
  items: ShopItemPreviewDto[];
  seller: SellerInfoDto;
  createdAt: string;
}
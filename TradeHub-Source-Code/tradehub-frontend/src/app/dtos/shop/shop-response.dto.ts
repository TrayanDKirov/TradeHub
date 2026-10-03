import { SellerInfoDto } from "../user/seller/seller-info.dto";

export interface ShopResponseDto {
  id: number;
  name: string;
  pictureUrl: string;
  seller: SellerInfoDto;
  createdAt: string;
}
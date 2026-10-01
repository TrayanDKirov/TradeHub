import { UserRegisterDto } from "../user-register.dto";

export interface BuyerRegisterDto extends UserRegisterDto {
    fullName: string;
    shippingStreet?: string;
    shippingCity?: string;
    shippingPostalCode?: string;
    shippingCountry?: string;
}

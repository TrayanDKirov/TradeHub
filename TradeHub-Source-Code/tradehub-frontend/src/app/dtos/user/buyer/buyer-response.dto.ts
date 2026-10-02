import { UserResponseDto } from "../user-response.dto";

export interface BuyerResponseDto extends UserResponseDto {
    role: "BUYER";
    fullName: string;
    shippingStreet?: string;
    shippingCity?: string;
    shippingPostalCode?: string;
    shippingCountry?: string;
}

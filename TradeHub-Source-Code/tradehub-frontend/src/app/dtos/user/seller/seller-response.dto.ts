import { UserResponseDto } from "../user-response.dto";

export interface SellerResponseDto extends UserResponseDto {
    role: "SELLER";
    displayName: string;
    contactEmail?: string;
    contactPhone?: string;
    description?: string;
}

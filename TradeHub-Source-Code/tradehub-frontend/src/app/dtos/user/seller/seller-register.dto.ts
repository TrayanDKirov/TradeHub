import { UserRegisterDto } from "../user-register.dto";

export interface SellerRegisterDto extends UserRegisterDto {
    displayName: string;
    contactEmail?: string;
    contactPhone?: string;
    description?: string;
}

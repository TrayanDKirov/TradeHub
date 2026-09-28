package bg.sofia.uni.fmi.tdkirov.tradehub.entity.user;


public enum Role {
    BUYER("buyer"),
    SELLER("seller"),
    ADMIN("admin");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }
}
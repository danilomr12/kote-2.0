export class AppUser {
  name: string;
  email: string;
  roles: string[];

  constructor(displayName: string, email: string, roles?: string[]) {
    this.name = displayName;
    this.email = email;
    roles ? (this.roles = roles) : (roles = []);
  }

}

export enum Roles {
  ADMIN = 'ROLE_ADMIN',
  REPRESENTANTE = 'ROLE_REPRESENTANTE',
  COMPRADOR = 'ROLE_COMPRADOR'
}

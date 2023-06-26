export class UserFull{
  id: number;
  username: string;
  password: string;
  email: string;
  userRole: string;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  authorities: any[];
  credentialsNonExpired: boolean;
  enabled: boolean;
}

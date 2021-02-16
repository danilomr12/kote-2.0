import {Pipe, PipeTransform} from '@angular/core';
import {AppUser, Roles} from '../models/app-user';

@Pipe({name: 'isUserAdmin', pure: true})
export class IsUserAdminPipe implements PipeTransform {

  transform(user: AppUser, ...args: any[]): boolean {
    if (!user) {
      return false;
    }
    return user.roles.includes(Roles.ADMIN);
  }
}

import {Injectable} from '@angular/core';
import {AuthService} from './auth.service';
import {map} from 'rxjs/operators';
import {CanActivate} from '@angular/router';
import {Observable} from 'rxjs';
import {Roles} from './models/app-user';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthGuardService implements CanActivate {

  constructor(private auth: AuthService) {
  }

  canActivate(): Observable<boolean> {
    return this.auth.appUser$.pipe(
      map(appUser => {
        console.log(appUser);
        console.log(Roles.ADMIN);
        console.log(appUser.roles.includes(Roles.ADMIN));
        return appUser.roles.includes(Roles.ADMIN);
      })
    );
  }
}

import {Injectable} from '@angular/core';
import {AngularFireAuth} from '@angular/fire/auth';
import * as firebase from 'firebase';
import {Observable, of} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {AppUser} from './models/app-user';
import {UserService} from './user.service';
import {switchMap} from 'rxjs/operators';

@Injectable()
export class AuthService {
  user$: Observable<firebase.default.User>;

  constructor(
    private afAuth: AngularFireAuth,
    private route: ActivatedRoute,
    private userService: UserService
  ) {
    this.user$ = afAuth.authState;
  }

  get appUser$(): Observable<AppUser> {
    return this.user$.pipe(
      switchMap(user => {
        if (user) {
          return this.userService.get(user.uid);
        }
        return of(null);
      })
    );
  }

  login(): void {
    const returnUrl = this.route.snapshot.queryParamMap.get('returnUrl') || '/';
    localStorage.setItem('returnUrl', returnUrl);
    this.afAuth.signInWithRedirect(new firebase.default.auth.GoogleAuthProvider());
  }

  logout(): void {
    this.afAuth.signOut()
      .catch(e => console.log('error: ' + e));
  }
}

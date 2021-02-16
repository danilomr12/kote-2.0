import {Injectable} from '@angular/core';
import {AngularFireDatabase} from '@angular/fire/database';
import * as firebase from 'firebase';
import {AppUser} from './models/app-user';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private db: AngularFireDatabase) {
  }

  save(user: firebase.default.User): void {
    this.db.object('/users/' + user.uid).update(
      new AppUser(user.displayName, user.email)
    );
  }

  get(uui: string): Observable<AppUser> {
    return this.db.object('/users/' + uui).valueChanges() as Observable<AppUser>;
  }
}

import {Component} from '@angular/core';
import {AuthService} from '../auth.service';
import {AppUser} from '../models/app-user';

@Component({
  selector: 'app-bs-nav-bar',
  templateUrl: './bs-nav-bar.component.html',
  styleUrls: ['./bs-nav-bar.component.css']
})
export class BsNavBarComponent {

  appUser: AppUser;

  constructor(private auth: AuthService) {
    auth.appUser$.subscribe(appUser => {
      this.appUser = appUser;
    });
  }

  logout(): void {
    this.auth.logout();
  }
}

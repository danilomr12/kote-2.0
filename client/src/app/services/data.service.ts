import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {empty} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  // tslint:disable-next-line:typedef
  private url = 'http://localhost:8080/produto';

  constructor(private http: HttpClient) {
  }

  // tslint:disable-next-line:typedef
  getAll() {
    return empty(); // this.http.get(this.url);
  }
}

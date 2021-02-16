import {Injectable} from '@angular/core';
import {AngularFireDatabase, AngularFireObject} from '@angular/fire/database';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Product} from './models/product';
import firebase from 'firebase';
import ThenableReference = firebase.database.ThenableReference;

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private db: AngularFireDatabase) {
  }

  getCategories(): Observable<any> {
    return this.db.list(
      '/product-categories',
      item => item.orderByChild('name'))
      .snapshotChanges();
  }

  saveProduct(product: any): ThenableReference {
    return this.db.list('/product').push(product) as any;
  }

  get(productId: string): AngularFireObject<any> {
    return this.db.object('/product/' + productId);
  }

  update(productId: string, product: any): Promise<void> {
    return this.db.object('/product/' + productId).update(product);
  }

  getAll(): Observable<any> {
    return this.db.list('/product').snapshotChanges().pipe(
      map(changes => {
        return changes.map(c => ({id: c.key, ...c.payload.val() as {}} as Product));
      })
    );
  }

  delete(productId: string): Promise<void> {
    return this.db.object('/product/' + productId).remove();
  }
}

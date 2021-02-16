import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {take} from 'rxjs/operators';
import {Product} from '../../models/product';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {

  categories$;
  product: Product = {} as Product;
  private id: string;

  constructor(
    private productService: ProductService,
    private router: Router,
    private acivateRoute: ActivatedRoute
  ) {
    this.categories$ = this.productService.getCategories();
    this.id = this.acivateRoute.snapshot.paramMap.get('id');

    if (this.id) {
      this.productService.get(this.id).valueChanges().pipe(
        take(1)
      ).subscribe((p: any) => {
        console.log('subscrito:');
        console.log(p);
        this.product = {id: this.id, ...p as {}} as Product;
        console.log(this.product);
      });
    } else {
      this.product = {} as Product;
    }
  }

  ngOnInit(): void {
  }

  save(value: any): void {
    if (this.id) {
      this.productService.update(this.id, value);
    } else {
      this.productService.saveProduct(value).then(result => {
        console.log(result);
      });
    }
    this.router.navigate(['/comp/catalogo']);
  }

  delete(): void {
    if (confirm('Tem certeza')) {
      this.productService.delete(this.id);
      this.router.navigate(['/comp/catalogo']);
    }
  }
}

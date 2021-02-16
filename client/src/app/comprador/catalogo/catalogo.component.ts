import {Component, OnDestroy, OnInit} from '@angular/core';
import {CatalogoDataSource} from './catalogo-datasource';
import {DataService} from '../../services/data.service';
import {ProductService} from '../../product.service';
import {Subscription} from 'rxjs';
import {Product} from '../../models/product';

@Component({
  selector: 'app-catalogo',
  templateUrl: './catalogo.component.html',
  styleUrls: ['./catalogo.component.css']
})
export class CatalogoComponent implements OnInit, OnDestroy {
  dataSource: CatalogoDataSource;
  public products: Product[];
  public filteredProducts: Product[];
  subscription: Subscription;

  constructor(
    private dataService: DataService,
    private productService: ProductService
  ) {
    this.subscription = this.productService.getAll().subscribe(resp => {
      this.filteredProducts = this.products = resp;
    });
  }

  ngOnInit(): void {
    this.dataSource = new CatalogoDataSource();
    this.dataService.getAll().subscribe(response => {
      console.log(response);
      this.dataSource.data = response as any;
    });
  }

  filter(value: string): void {
    console.log(value);
    this.filteredProducts = (value) ?
      this.products.filter(item => item.description.toLowerCase().includes(value.toLowerCase())) :
      this.products;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}

import {Component} from '@angular/core';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material/tree';

/**
 * Food data with nested structure.
 * Each node has a name and an optional list of children.
 */
interface FoodNode {
  name: string;
  children?: FoodNode[];
}


const TREE_DATA: FoodNode[] = [
  {
    name: 'Cotação volta as aulas - 15/01/2021',
    children: [
      {name: 'JC - Atacadista - João Gabriel'},
      {name: 'Megafort Distribuidora - Emerson'},
      {name: 'Eldorado distribuição - Paulo '},
    ]
  },
  {
    name: 'Cotação Natal - 10/12/2020',
    children: [
      {
        name: 'JC - Atacadista - João Gabriel',
        children: [
          {name: 'Pedido - 11/12/2020 14:36 - Faturado'},
          {name: 'Pedido faltas - 11/12/2020 16:56 - Faturado'},
        ]
      }, {
        name: 'Megafort Distribuidora - Emerson',
        children: [
          {name: 'Pedido - 11/12/2020 15:25 - Faturado'},
          {name: 'Pedido faltas - 11/12/2020 17:36 - Faturado'},
        ]
      },
    ]
  },
];

/**
 * @title Tree with nested nodes
 */

@Component({
  selector: 'app-caixa-de-entrada',
  templateUrl: './caixa-de-entrada.component.html',
  styleUrls: ['./caixa-de-entrada.component.css']
})
export class CaixaDeEntradaComponent {

  treeControl = new NestedTreeControl<FoodNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<FoodNode>();

  constructor() {
    this.dataSource.data = TREE_DATA;
  }

  hasChild = (_: number, node: FoodNode) => !!node.children && node.children.length > 0;
}


/**  Copyright 2020 Google LLC. All Rights Reserved.
 * Use of this source code is governed by an MIT-style license that
 * can be found in the LICENSE file at http://angular.io/license
 */

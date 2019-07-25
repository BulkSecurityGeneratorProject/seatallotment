import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeatPartition } from 'app/shared/model/seat-partition.model';

@Component({
  selector: 'jhi-seat-partition-detail',
  templateUrl: './seat-partition-detail.component.html'
})
export class SeatPartitionDetailComponent implements OnInit {
  seatPartition: ISeatPartition;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ seatPartition }) => {
      this.seatPartition = seatPartition;
    });
  }

  previousState() {
    window.history.back();
  }
}

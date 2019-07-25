import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISeatPartition } from 'app/shared/model/seat-partition.model';
import { AccountService } from 'app/core';
import { SeatPartitionService } from './seat-partition.service';

@Component({
  selector: 'jhi-seat-partition',
  templateUrl: './seat-partition.component.html'
})
export class SeatPartitionComponent implements OnInit, OnDestroy {
  seatPartitions: ISeatPartition[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected seatPartitionService: SeatPartitionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.seatPartitionService
      .query()
      .pipe(
        filter((res: HttpResponse<ISeatPartition[]>) => res.ok),
        map((res: HttpResponse<ISeatPartition[]>) => res.body)
      )
      .subscribe(
        (res: ISeatPartition[]) => {
          this.seatPartitions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSeatPartitions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISeatPartition) {
    return item.id;
  }

  registerChangeInSeatPartitions() {
    this.eventSubscriber = this.eventManager.subscribe('seatPartitionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

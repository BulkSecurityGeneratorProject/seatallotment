import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDistrict } from 'app/shared/model/district.model';
import { AccountService } from 'app/core';
import { DistrictService } from './district.service';

@Component({
  selector: 'jhi-district',
  templateUrl: './district.component.html'
})
export class DistrictComponent implements OnInit, OnDestroy {
  districts: IDistrict[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected districtService: DistrictService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.districtService
      .query()
      .pipe(
        filter((res: HttpResponse<IDistrict[]>) => res.ok),
        map((res: HttpResponse<IDistrict[]>) => res.body)
      )
      .subscribe(
        (res: IDistrict[]) => {
          this.districts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDistricts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDistrict) {
    return item.id;
  }

  registerChangeInDistricts() {
    this.eventSubscriber = this.eventManager.subscribe('districtListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

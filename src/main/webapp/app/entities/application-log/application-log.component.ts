import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApplicationLog } from 'app/shared/model/application-log.model';
import { AccountService } from 'app/core';
import { ApplicationLogService } from './application-log.service';

@Component({
  selector: 'jhi-application-log',
  templateUrl: './application-log.component.html'
})
export class ApplicationLogComponent implements OnInit, OnDestroy {
  applicationLogs: IApplicationLog[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected applicationLogService: ApplicationLogService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.applicationLogService
      .query()
      .pipe(
        filter((res: HttpResponse<IApplicationLog[]>) => res.ok),
        map((res: HttpResponse<IApplicationLog[]>) => res.body)
      )
      .subscribe(
        (res: IApplicationLog[]) => {
          this.applicationLogs = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApplicationLogs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApplicationLog) {
    return item.id;
  }

  registerChangeInApplicationLogs() {
    this.eventSubscriber = this.eventManager.subscribe('applicationLogListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

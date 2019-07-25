import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApplicationlog } from 'app/shared/model/applicationlog.model';
import { AccountService } from 'app/core';
import { ApplicationlogService } from './applicationlog.service';

@Component({
  selector: 'jhi-applicationlog',
  templateUrl: './applicationlog.component.html'
})
export class ApplicationlogComponent implements OnInit, OnDestroy {
  applicationlogs: IApplicationlog[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected applicationlogService: ApplicationlogService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.applicationlogService
      .query()
      .pipe(
        filter((res: HttpResponse<IApplicationlog[]>) => res.ok),
        map((res: HttpResponse<IApplicationlog[]>) => res.body)
      )
      .subscribe(
        (res: IApplicationlog[]) => {
          this.applicationlogs = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApplicationlogs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApplicationlog) {
    return item.id;
  }

  registerChangeInApplicationlogs() {
    this.eventSubscriber = this.eventManager.subscribe('applicationlogListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationLog } from 'app/shared/model/application-log.model';
import { ApplicationLogService } from './application-log.service';

@Component({
  selector: 'jhi-application-log-delete-dialog',
  templateUrl: './application-log-delete-dialog.component.html'
})
export class ApplicationLogDeleteDialogComponent {
  applicationLog: IApplicationLog;

  constructor(
    protected applicationLogService: ApplicationLogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.applicationLogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'applicationLogListModification',
        content: 'Deleted an applicationLog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-application-log-delete-popup',
  template: ''
})
export class ApplicationLogDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationLog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApplicationLogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.applicationLog = applicationLog;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/application-log', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/application-log', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISeatPartition } from 'app/shared/model/seat-partition.model';
import { SeatPartitionService } from './seat-partition.service';

@Component({
  selector: 'jhi-seat-partition-delete-dialog',
  templateUrl: './seat-partition-delete-dialog.component.html'
})
export class SeatPartitionDeleteDialogComponent {
  seatPartition: ISeatPartition;

  constructor(
    protected seatPartitionService: SeatPartitionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.seatPartitionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'seatPartitionListModification',
        content: 'Deleted an seatPartition'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-seat-partition-delete-popup',
  template: ''
})
export class SeatPartitionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ seatPartition }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SeatPartitionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.seatPartition = seatPartition;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/seat-partition', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/seat-partition', { outlets: { popup: null } }]);
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

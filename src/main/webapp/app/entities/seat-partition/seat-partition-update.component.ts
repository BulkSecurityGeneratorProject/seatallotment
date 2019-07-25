import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISeatPartition, SeatPartition } from 'app/shared/model/seat-partition.model';
import { SeatPartitionService } from './seat-partition.service';

@Component({
  selector: 'jhi-seat-partition-update',
  templateUrl: './seat-partition-update.component.html'
})
export class SeatPartitionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    district: [],
    total: [],
    vacant: [],
    occupied: []
  });

  constructor(protected seatPartitionService: SeatPartitionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ seatPartition }) => {
      this.updateForm(seatPartition);
    });
  }

  updateForm(seatPartition: ISeatPartition) {
    this.editForm.patchValue({
      id: seatPartition.id,
      district: seatPartition.district,
      total: seatPartition.total,
      vacant: seatPartition.vacant,
      occupied: seatPartition.occupied
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const seatPartition = this.createFromForm();
    if (seatPartition.id !== undefined) {
      this.subscribeToSaveResponse(this.seatPartitionService.update(seatPartition));
    } else {
      this.subscribeToSaveResponse(this.seatPartitionService.create(seatPartition));
    }
  }

  private createFromForm(): ISeatPartition {
    return {
      ...new SeatPartition(),
      id: this.editForm.get(['id']).value,
      district: this.editForm.get(['district']).value,
      total: this.editForm.get(['total']).value,
      vacant: this.editForm.get(['vacant']).value,
      occupied: this.editForm.get(['occupied']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeatPartition>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

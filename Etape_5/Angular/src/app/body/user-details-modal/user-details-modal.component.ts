import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UserDTO } from 'src/app/dto/UserDTO';
@Component({
  selector: 'app-user-details-modal',
  templateUrl: './user-details-modal.component.html',
  styleUrls: ['./user-details-modal.component.scss']
})
export class UserDetailsModalComponent implements OnInit {

  @Input() user: UserDTO;

  constructor(public activeModal: NgbActiveModal) {}


  ngOnInit(): void {
  }

}

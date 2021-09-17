package com.rasmoo.client.financescontroll.v1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rasmoo.client.financescontroll.entity.Category;
import com.rasmoo.client.financescontroll.entity.Entry;
import com.rasmoo.client.financescontroll.entity.User;
import com.rasmoo.client.financescontroll.repository.ICategoryRepository;
import com.rasmoo.client.financescontroll.repository.IEntryRepository;
import com.rasmoo.client.financescontroll.v1.service.IUserInfoService;
import com.rasmoo.client.financescontroll.v1.vo.EntryVO;
import com.rasmoo.client.financescontroll.v1.vo.Response;

@RestController
@CrossOrigin
@RequestMapping("/v1/lancamento")
@PreAuthorize(value = "#oauth2.hasScope('cw_logado') and hasAnyRole('ROLE_FC_ADM','ROLE_CUSTOMER')")
public class EntryController {

	@Autowired
	private IEntryRepository entryRepository;

	@Autowired
	private ICategoryRepository categoryRepository;

	@Autowired
	private IUserInfoService userInfoService;

	@PostMapping
	public ResponseEntity<Response<Entry>> cadastrarLancamento(@RequestBody EntryVO entryVO) {
		Response<Entry> response = new Response<>();
		try {
			User usuario = this.userInfoService.findAuth();
			Optional<Category> category = this.categoryRepository.findByUserId(entryVO.getCategoryId(), usuario.getId());
			if (entryVO.getId() == null && category.isPresent()) {

				Entry lancamento = new Entry();

				lancamento.setCategoria(category.get());
				lancamento.setTipo(entryVO.getTipo());
				lancamento.setValor(entryVO.getValor());
				lancamento.setUser(usuario);
				response.setData(this.entryRepository.save(lancamento));
				response.setStatusCode(HttpStatus.CREATED.value());
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}
			throw new Exception();
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@PutMapping
	public ResponseEntity<Response<Entry>> atualizarLancamento(@RequestBody EntryVO entryVO) {
		Response<Entry> response = new Response<>();
		try {
			User usuario = this.userInfoService.findAuth();
			Optional<Category> category = this.categoryRepository.findByUserId(entryVO.getCategoryId(), usuario.getId());
			Optional<Entry> entry = this.entryRepository.findById(entryVO.getId());
			if (entry.isPresent() && category.isPresent()) {
				Entry lancamento = entry.get();
				lancamento.setCategoria(category.get());
				lancamento.setTipo(entryVO.getTipo());
				lancamento.setValor(entryVO.getValor());

				response.setData(this.entryRepository.save(lancamento));
				response.setStatusCode(HttpStatus.OK.value());
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			throw new Exception();
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
	
	@PreAuthorize(value = "#oauth2.hasAnyScope('cc_logado','cw_logado')")
	@GetMapping
	public ResponseEntity<Response<List<Entry>>> listarLancamentos() {
		Response<List<Entry>> response = new Response<>();
		try {

			User usuario = this.userInfoService.findAuth();
			response.setData(this.entryRepository.findAllByUserId(usuario.getId()));
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
	
	@PreAuthorize(value = "#oauth2.hasAnyScope('cc_logado','cw_logado')")
	@GetMapping("/{id}")
	public ResponseEntity<Response<Entry>> consultarLancamento(@PathVariable("id") long id) {
		Response<Entry> response = new Response<>();
		try {
			Optional<Entry> entry = this.entryRepository.findById(id);
			if (entry.isPresent()) {
				response.setData(entry.get());
			}
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Boolean>> excluirLancamento(@PathVariable("id") long id) {
		Response<Boolean> response = new Response<>();
		try {
			if (this.entryRepository.findById(id).isPresent()) {
				this.entryRepository.deleteById(id);
				response.setData(Boolean.TRUE);
			}
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
}

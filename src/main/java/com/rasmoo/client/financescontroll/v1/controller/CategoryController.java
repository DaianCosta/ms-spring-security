package com.rasmoo.client.financescontroll.v1.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
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
import com.rasmoo.client.financescontroll.entity.User;
import com.rasmoo.client.financescontroll.repository.ICategoryRepository;
import com.rasmoo.client.financescontroll.v1.dto.CategoryDTO;
import com.rasmoo.client.financescontroll.v1.service.IUserInfoService;
import com.rasmoo.client.financescontroll.v1.vo.Response;

@RestController
@CrossOrigin
@RequestMapping("/v1/categoria")
@PreAuthorize(value = "#oauth2.hasScope('cw_logado') and hasAnyRole('ROLE_FC_ADM','ROLE_CUSTOMER')")
public class CategoryController {
	
	private static Logger logger = LogManager.getLogger(CategoryController.class);

	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Autowired
	private IUserInfoService userInfoService;

	private ModelMapper mapper = new ModelMapper();

	@PostMapping
	public ResponseEntity<Response<Category>> cadastrarCategoria(@RequestBody CategoryDTO categoria) {
		Response<Category> response = new Response<>();
		try {

			User usuario = this.userInfoService.findAuth();

			if (categoria != null && categoria.getId() == null) {
				Category categoriaEntity = mapper.map(categoria, Category.class);
				categoriaEntity.setUser(usuario);
				response.setData(this.categoryRepository.save(categoriaEntity));
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
	public ResponseEntity<Response<Category>> atualizarCategoria(@RequestBody CategoryDTO categoria) {
		Response<Category> response = new Response<>();
		try {
			if (categoria != null && categoria.getId() != null) {
				User usuario = this.userInfoService.findAuth();

				Optional<Category> category = this.categoryRepository.findByUserId(categoria.getId(), usuario.getId());
				if (category.isPresent() && category.get().getUser().getId() == usuario.getId()) {
					Category categoriaEntity = mapper.map(categoria, Category.class);
					categoriaEntity.setUser(usuario);
					response.setData(this.categoryRepository.save(categoriaEntity));
					response.setStatusCode(HttpStatus.OK.value());
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}
			}
			throw new Exception();
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
	
	@PreAuthorize(value = "#oauth2.hasAnyScope('cc_logado','cw_logado') and hasAnyRole('ROLE_FC_ADM','ROLE_CUSTOMER')")
	@GetMapping
	public ResponseEntity<Response<List<Category>>> listarCategorias() {
		Response<List<Category>> response = new Response<>();
		try {
			User usuario = this.userInfoService.findAuth();
			response.setData(this.categoryRepository.findAllByUserId(usuario.getId()));
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			logger.error(e);
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
	
	@PreAuthorize(value = "#oauth2.hasAnyScope('cc_logado','cw_logado') and hasAnyRole('ROLE_FC_ADM','ROLE_CUSTOMER')")
	@GetMapping("/{id}")
	public ResponseEntity<Response<Category>> consultarCategoria(@PathVariable Long id) {
		Response<Category> response = new Response<>();
		try {
			Optional<Category> catogory = this.categoryRepository.findById(id);
			if (catogory.isPresent()) {
				response.setData(catogory.get());
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
	public ResponseEntity<Response<Boolean>> excluirCategoria(@PathVariable Long id) {
		Response<Boolean> response = new Response<>();
		try {
			if (this.categoryRepository.findById(id).isPresent()) {
				this.categoryRepository.deleteById(id);
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

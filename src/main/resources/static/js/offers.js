/*<![CDATA[*/
var offers = /*[[${offers}]]*/ [];
var offersWithQuantity;

var totalPrice = 0;

document.addEventListener('DOMContentLoaded', function() {
    if (sessionStorage.getItem('offers')) {
        offersWithQuantity = JSON.parse(sessionStorage.getItem('offers'));
        offersWithQuantity.forEach(offer => { // do not store totalPrice into session storage to prevent manipulation of it
            totalPrice += offer.quantity * offer.price;
        });
        document.getElementById('totalPrice').textContent = `${totalPrice.toFixed(2)}€`;
    } else {
        offersWithQuantity = offers.map(offer => ({
            id: offer.id,
            name: offer.name,
            price: offer.price,
            quantity: 0
        }));
        sessionStorage.setItem('offers', JSON.stringify(offersWithQuantity));
    }

    const offersContainer = document.getElementById('offersContainer');

    offersWithQuantity.forEach((offer, index) => {
        const card = document.createElement('div');
        card.className = 'col';
        card.innerHTML = `
        <div class="card h-100 shadow-sm">
          <div class="card-body">
            <h5 class="card-title">${offer.name}</h5>
            <p class="card-text" id="price${index}">Price: ${parseFloat(offer.price).toFixed(2)}€</p>
            <div class="d-flex justify-content-center align-items-center mb-3 quantity-selectors">
              <button class="btn btn-outline-secondary change-quantity rounded-circle" data-id="${index}" data-action="decrease">-</button>
              <span class="text-center quantity" id="quantity${index}" style="min-width: 60px;">${offer.quantity}</span>
              <button class="btn btn-outline-secondary change-quantity rounded-circle" data-id="${index}" data-action="increase">+</button>
            </div>
          </div>
        </div>
      `;
        offersContainer.appendChild(card);
    });

    updateEventListeners();
});

function updateEventListeners() {
    document.querySelectorAll('.change-quantity').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.dataset.id;
            const action = this.dataset.action;
            const quantitySpan = document.querySelector(`#quantity${id}`);

            let quantity = parseInt(quantitySpan.textContent);

            if (action === 'increase') {
                quantity++;
                updateTotal(id, action);
            } else if (action === 'decrease' && quantity > 0) {
                quantity--;
                updateTotal(id, action);
            }

            quantitySpan.textContent = quantity;

            offersWithQuantity[id].quantity = quantity;
            sessionStorage.setItem('offers', JSON.stringify(offersWithQuantity));
        });
    });
}

function updateTotal(id, action) {
    const priceText = document.getElementById(`price${id}`).textContent;
    const price = parseFloat(priceText.replace('Price: ', '').replace('€',''));

    if (action === 'increase') {
        totalPrice += price;
    } else if (action === 'decrease') {
        totalPrice -= price;
    }

    document.getElementById('totalPrice').textContent = `${totalPrice.toFixed(2)}€`;
}


/*]]>*/

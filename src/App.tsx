import React, { useState, useEffect } from 'react';
import { DollarSign, ArrowRightLeft } from 'lucide-react';

interface ExchangeRates {
  [key: string]: number;
}

function App() {
  const [amount, setAmount] = useState<string>('');
  const [fromCurrency, setFromCurrency] = useState<string>('USD');
  const [toCurrency, setToCurrency] = useState<string>('ARS');
  const [convertedAmount, setConvertedAmount] = useState<string>('');
  const [rates, setRates] = useState<ExchangeRates>({});
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');

  const currencies = [
    { code: 'ARS', name: 'Peso Argentino' },
    { code: 'BOB', name: 'Boliviano' },
    { code: 'BRL', name: 'Real Brasileño' },
    { code: 'CLP', name: 'Peso Chileno' },
    { code: 'COP', name: 'Peso Colombiano' },
    { code: 'USD', name: 'Dólar Estadounidense' }
  ];

  useEffect(() => {
    fetchExchangeRates();
  }, []);

  const fetchExchangeRates = async () => {
    try {
      setLoading(true);
      setError('');
      const response = await fetch(`https://v6.exchangerate-api.com/v6/d2964208e056adacd9eec684/latest/USD`);
      const data = await response.json();
      setRates(data.conversion_rates);
    } catch (err) {
      setError('Error al obtener las tasas de cambio. Por favor, intente nuevamente.');
    } finally {
      setLoading(false);
    }
  };

  const handleConvert = () => {
    if (!amount || !rates[fromCurrency] || !rates[toCurrency]) return;

    const amountNum = parseFloat(amount);
    if (isNaN(amountNum)) return;

    let result: number;
    if (fromCurrency === 'USD') {
      result = amountNum * rates[toCurrency];
    } else if (toCurrency === 'USD') {
      result = amountNum / rates[fromCurrency];
    } else {
      const amountInUSD = amountNum / rates[fromCurrency];
      result = amountInUSD * rates[toCurrency];
    }

    setConvertedAmount(result.toFixed(2));
  };

  const handleSwapCurrencies = () => {
    setFromCurrency(toCurrency);
    setToCurrency(fromCurrency);
    setConvertedAmount('');
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-100 to-blue-200 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md mx-auto bg-white rounded-xl shadow-lg overflow-hidden">
        <div className="p-8">
          <div className="flex items-center justify-center mb-6">
            <DollarSign className="h-12 w-12 text-blue-500" />
            <h1 className="ml-3 text-2xl font-bold text-gray-900">Conversor de Moneda</h1>
          </div>

          {error && (
            <div className="mb-4 p-3 bg-red-100 text-red-700 rounded-md">
              {error}
            </div>
          )}

          <div className="space-y-6">
            <div>
              <label htmlFor="amount" className="block text-sm font-medium text-gray-700">
                Cantidad
              </label>
              <div className="mt-1">
                <input
                  type="number"
                  name="amount"
                  id="amount"
                  className="shadow-sm focus:ring-blue-500 focus:border-blue-500 block w-full sm:text-sm border-gray-300 rounded-md"
                  value={amount}
                  onChange={(e) => {
                    setAmount(e.target.value);
                    setConvertedAmount('');
                  }}
                  placeholder="0.00"
                />
              </div>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <label htmlFor="from-currency" className="block text-sm font-medium text-gray-700">
                  De
                </label>
                <select
                  id="from-currency"
                  name="from-currency"
                  className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm rounded-md"
                  value={fromCurrency}
                  onChange={(e) => {
                    setFromCurrency(e.target.value);
                    setConvertedAmount('');
                  }}
                >
                  {currencies.map((currency) => (
                    <option key={currency.code} value={currency.code}>
                      {currency.code} - {currency.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="flex flex-col">
                <label className="block text-sm font-medium text-gray-700 invisible">
                  Cambiar
                </label>
                <button
                  type="button"
                  onClick={handleSwapCurrencies}
                  className="mt-1 p-2 border border-gray-300 rounded-md hover:bg-gray-50"
                >
                  <ArrowRightLeft className="h-5 w-5 text-gray-500" />
                </button>
              </div>

              <div>
                <label htmlFor="to-currency" className="block text-sm font-medium text-gray-700">
                  A
                </label>
                <select
                  id="to-currency"
                  name="to-currency"
                  className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm rounded-md"
                  value={toCurrency}
                  onChange={(e) => {
                    setToCurrency(e.target.value);
                    setConvertedAmount('');
                  }}
                >
                  {currencies.map((currency) => (
                    <option key={currency.code} value={currency.code}>
                      {currency.code} - {currency.name}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            <button
              type="button"
              onClick={handleConvert}
              disabled={loading || !amount}
              className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:bg-blue-300 disabled:cursor-not-allowed"
            >
              {loading ? 'Cargando...' : 'Convertir'}
            </button>

            {convertedAmount && (
              <div className="mt-4 p-4 bg-green-50 rounded-md">
                <p className="text-center text-lg font-medium text-green-800">
                  {amount} {fromCurrency} = {convertedAmount} {toCurrency}
                </p>
              </div>
            )}
          </div>

          <div className="mt-6">
            <img
              src="https://images.pexels.com/photos/534216/pexels-photo-534216.jpeg"
              alt="Monedas del mundo"
              className="w-full h-32 object-cover rounded-lg"
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;